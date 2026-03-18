use chrono::NaiveDate;
use dotenvy::dotenv;
use mysql::prelude::*;
use mysql::{params, Opts, Pool, PooledConn, Result};
use std::env;

use crate::domain::user::{User, UserType};
use crate::persistence::traits::UserRepository;

pub struct MySqlUserRepository {
    pool: Pool,
}

impl MySqlUserRepository {
    pub fn new() -> Self {
        dotenv().ok();

        let database_url = env::var("DATABASE_URL").expect("DATABASE_URL must be set");
        let opts = Opts::from_url(&database_url).unwrap();
        let pool = Pool::new(opts).unwrap();

        Self { pool }
    }

    fn get_conn(&self) -> Result<PooledConn> {
        self.pool.get_conn()
    }
}

impl UserRepository for MySqlUserRepository {
    fn insert_user(&self, user: User) -> Result<u64> {
        let mut conn = self.get_conn()?;

        conn.exec_drop(
            r#"
            INSERT INTO users (
                age,
                display_name,
                nationality,
                password,
                username
            ) VALUES (
                :age,
                :display_name,
                :nationality,
                :password,
                :username
            )
            "#,
            params! {
                "age" => user.age.clone(),
                "display_name" => user.display_name.clone(),
                "nationality" => user.nationality.clone(),
                "password" => user.password.clone(),
                "username" => user.username.clone(),
            },
        )?;

        let return_value = conn.last_insert_id();

        if user.user_type == UserType::Admin {
            let user_row: Option<u32> = conn.exec_first(
                r#"
                SELECT
                    id
                FROM users
                WHERE username = :username
                "#,
                params! {
                    "username" => user.username.clone(),
                },
            )?;

            let Some(admin_id) = user_row else {
                return Ok(0)
            };

            conn.exec_drop(
                r#"
                INSERT INTO admin_entity (
                    address,
                    contract_end_date,
                    contract_start_date,
                    monthly_salary,
                    id
                ) VALUES (
                    :address,
                    :contract_end_date,
                    :contract_start_date,
                    :monthly_salary,
                    :id
                )
                "#,
                params! {
                    "address" => user.address.clone(),
                    "contract_end_date" => user.contract_end_date.as_ref().map(|d| d.to_string()).clone(),
                    "contract_start_date" => user.contract_start_date.as_ref().map(|d| d.to_string()).clone(),
                    "monthly_salary" => user.monthly_salary,
                    "id" => admin_id,
                },
            )?;
        } else if user.user_type == UserType::ProfecionalPlayer {
            let user_row: Option<u32> = conn.exec_first(
                r#"
                SELECT
                    id
                FROM users
                WHERE username = :username
                "#,
                params! {
                    "username" => user.username.clone(),
                },
            )?;

            let Some(pro_id) = user_row else {
                return Ok(0)
            };

            conn.exec_drop(
                r#"
                INSERT INTO professional_player_entity (
                    follower_count,
                    no_of_games_played,
                    player_elo,
                    win_rate,
                    id
                ) VALUES (
                    :follower_count,
                    :no_of_games_played,
                    :player_elo,
                    :win_rate,
                    :id
                )
                "#,
                params! {
                    "follower_count" => user.follower_count.clone(),
                    "no_of_games_played" => user.no_of_pro_games_played.clone(),
                    "player_elo" => user.pro_player_elo.clone(),
                    "win_rate" => user.win_rate,
                    "id" => pro_id,
                },
            )?;
        } else {
            let user_row: Option<u32> = conn.exec_first(
                r#"
                SELECT
                    id
                FROM users
                WHERE username = :username
                "#,
                params! {
                    "username" => user.username.clone(),
                },
            )?;

            let Some(spec_id) = user_row else {
                return Ok(0)
            };

            conn.exec_drop(
                r#"
                INSERT INTO spectator_player_entity (
                    has_pass,
                    is_chat_banned,
                    is_game_banned,
                    no_of_games_played,
                    player_elo,
                    id
                ) VALUES (
                    :has_pass,
                    :is_chat_banned,
                    :is_game_banned,
                    :no_of_games_played,
                    :player_elo,
                    :id
                )
                "#,
                params! {
                    "has_pass" => user.has_pass.clone(),
                    "is_chat_banned" => user.is_chat_banned.clone(),
                    "is_game_banned" => user.is_game_banned.clone(),
                    "no_of_games_played" => user.no_of_spec_games_played.clone(),
                    "player_elo" => user.spec_player_elo,
                    "id" => spec_id,
                },
            )?;
        }

        Ok(return_value)
    }

    fn delete_user(&self, user: User) -> Result<()> {
        let mut conn = self.get_conn()?;
        let user_id = user.id as u64;

        conn.exec_drop(
            "DELETE FROM users WHERE id = :id",
            params! {
                "id" => user_id,
            },
        )?;

        if user.user_type == UserType::Admin {
            conn.exec_drop(
                "DELETE FROM admin_entity WHERE id = :id",
                params! {
                    "id" => user_id,
                },
            )?;
        } else if user.user_type == UserType::ProfecionalPlayer {
            conn.exec_drop(
                "DELETE FROM professional_player_entity WHERE id = :id",
                params! {
                    "id" => user_id,
                },
            )?;
        } else {
            conn.exec_drop(
                "DELETE FROM spectator_player_entity WHERE id = :id",
                params! {
                    "id" => user_id,
                },
            )?;
        }

        Ok(())
    }

    fn find_user_by_id(&self, user_id: u32) -> Result<Option<User>> {
        let mut conn = self.get_conn()?;

        let is_admin: Option<u8> = conn.exec_first(
            r#"
            SELECT EXISTS (
                SELECT 1
                FROM admin_entity
                WHERE id = :id
            )
            AS exists_flag
            "#,
            params! {
                "id" => user_id,
            },
        )?;

        let is_pro: Option<u8> = conn.exec_first(
            r#"
            SELECT EXISTS (
                SELECT 1
                FROM professional_player_entity
                WHERE id = :id
            )
            AS exists_flag
            "#,
            params! {
                "id" => user_id,
            },
        )?;

        let is_spec: Option<u8> = conn.exec_first(
            r#"
            SELECT EXISTS (
                SELECT 1
                FROM spectator_player_entity
                WHERE id = :id
            )
            AS exists_flag
            "#,
            params! {
                "id" => user_id,
            },
        )?;

        if is_admin == Some(1) {
            let user_row: Option<(
                u32,
                String,
                String,
                u32,
                String,
                String,
                f32,
                String,
                String,
                String,
            )> = conn.exec_first(
                r#"
                SELECT
                    u.id,
                    u.username,
                    u.password,
                    u.age,
                    u.display_name,
                    u.nationality,
                    a.monthly_salary,
                    a.contract_start_date,
                    a.contract_end_date,
                    a.address
                FROM users u
                LEFT JOIN admin_entity a
                    ON u.id = a.id
                WHERE u.id = :id
                LIMIT 1
                "#,
                params! {
                    "id" => user_id,
                },
            )?;

            let Some((id, username, password, age, display_name, nationality, monthly_salary, contract_start_date, contract_end_date, address)) = user_row else { 
                return Ok(None);
            };

            let return_user: User = User::new_admin(
                id, 
                username, 
                password, 
                age as u8, 
                display_name, 
                nationality, 
                monthly_salary, 
                NaiveDate::parse_from_str(&contract_start_date, "%Y-%m-%d").unwrap(), 
                NaiveDate::parse_from_str(&contract_end_date, "%Y-%m-%d").unwrap(), 
                address);

            return Ok(Some(return_user));
        } else if is_pro == Some(1) {
            let user_row: Option<(
                u32,
                String,
                String,
                u32,
                String,
                String,
                u32,
                f32,
                u32,
                u32,
            )> = conn.exec_first(
                r#"
                SELECT
                    u.id,
                    u.username,
                    u.password,
                    u.age,
                    u.display_name,
                    u.nationality,
                    p.player_elo,
                    p.win_rate,
                    p.no_of_games_played,
                    p.follower_count
                FROM users u
                LEFT JOIN professional_player_entity p
                    ON u.id = p.id
                WHERE u.id = :id
                LIMIT 1
                "#,
                params! {
                    "id" => user_id,
                },
            )?;

            let Some((id, username, password, age, display_name, nationality, pro_player_elo, win_rate, no_of_pro_games_played, follower_count)) = user_row else {
                return Ok(None);
            };

            let return_user: User = User::new_pro(
                id, 
                username, 
                password, 
                age as u8, 
                display_name, 
                nationality, 
                pro_player_elo,
                win_rate,
                no_of_pro_games_played,
                follower_count
            );

            return Ok(Some(return_user));
        } else if is_spec == Some(1) {
            let user_row: Option<(
                u32,
                String,
                String,
                u32,
                String,
                String,
                u32,
                bool,
                bool,
                u32,
                bool,
            )> = conn.exec_first(
                r#"
                SELECT
                    u.id,
                    u.username,
                    u.password,
                    u.age,
                    u.display_name,
                    u.nationality,
                    s.player_elo,
                    s.is_chat_banned,
                    s.is_game_banned,
                    s.no_of_games_played
                FROM users u
                LEFT JOIN spectator_player_entity s
                    ON u.id = s.id
                WHERE u.id = :id
                LIMIT 1
                "#,
                params! {
                    "id" => user_id,
                }
            )?;

            let Some((id, username, password, age, display_name, nationality, spec_player_elo, is_chat_banned, is_game_banned, no_of_spec_games_played, has_pass)) = user_row else {
                return Ok(None);
            };

            let return_user: User = User::new_spec(
                id,
                username,
                password,
                age as u8,
                display_name,
                nationality,
                spec_player_elo,
                is_chat_banned,
                is_game_banned,
                no_of_spec_games_played,
                has_pass
            );

            return Ok(Some(return_user));
        } else {
            Ok(None)
        }
    }

    fn find_user_by_username(&self, username: &str) -> Result<Option<User>> {
        let mut conn = self.get_conn()?;

        let user_id: Option<u32> = conn.exec_first(
           r#"
            SELECT id
            FROM users
            WHERE username = ":username"
           "#, 
            params! {
                "username" => username,
            }
        )?;

        if user_id == None {
            return Ok(None);
        }

        let is_admin: Option<u8> = conn.exec_first(
            r#"
            SELECT EXISTS (
                SELECT 1
                FROM admin_entity
                WHERE id = :id
            )
            AS exists_flag
            "#,
            params! {
                "id" => user_id,
            },
        )?;

        let is_pro: Option<u8> = conn.exec_first(
            r#"
            SELECT EXISTS (
                SELECT 1
                FROM professional_player_entity
                WHERE id = :id
            )
            AS exists_flag
            "#,
            params! {
                "id" => user_id,
            },
        )?;

        let is_spec: Option<u8> = conn.exec_first(
            r#"
            SELECT EXISTS (
                SELECT 1
                FROM spectator_player_entity
                WHERE id = :id
            )
            AS exists_flag
            "#,
            params! {
                "id" => user_id,
            },
        )?;

        if is_admin == Some(1) {
            let user_row: Option<(
                u32,
                String,
                String,
                u32,
                String,
                String,
                f32,
                String,
                String,
                String,
            )> = conn.exec_first(
                r#"
                SELECT
                    u.id,
                    u.username,
                    u.password,
                    u.age,
                    u.display_name,
                    u.nationality,
                    a.monthly_salary,
                    a.contract_start_date,
                    a.contract_end_date,
                    a.address
                FROM users u
                LEFT JOIN admin_entity a
                    ON u.id = a.id
                WHERE u.id = :id
                LIMIT 1
                "#,
                params! {
                    "id" => user_id,
                },
            )?;

            let Some((id, username, password, age, display_name, nationality, monthly_salary, contract_start_date, contract_end_date, address)) = user_row else { 
                return Ok(None);
            };

            let return_user: User = User::new_admin(
                id, 
                username, 
                password, 
                age as u8, 
                display_name, 
                nationality, 
                monthly_salary, 
                NaiveDate::parse_from_str(&contract_start_date, "%Y-%m-%d").unwrap(), 
                NaiveDate::parse_from_str(&contract_end_date, "%Y-%m-%d").unwrap(), 
                address);

            return Ok(Some(return_user));
        } else if is_pro == Some(1) {
            let user_row: Option<(
                u32,
                String,
                String,
                u32,
                String,
                String,
                u32,
                f32,
                u32,
                u32,
            )> = conn.exec_first(
                r#"
                SELECT
                    u.id,
                    u.username,
                    u.password,
                    u.age,
                    u.display_name,
                    u.nationality,
                    p.player_elo,
                    p.win_rate,
                    p.no_of_games_played,
                    p.follower_count
                FROM users u
                LEFT JOIN professional_player_entity p
                    ON u.id = p.id
                WHERE u.id = :id
                LIMIT 1
                "#,
                params! {
                    "id" => user_id,
                },
            )?;

            let Some((id, username, password, age, display_name, nationality, pro_player_elo, win_rate, no_of_pro_games_played, follower_count)) = user_row else {
                return Ok(None);
            };

            let return_user: User = User::new_pro(
                id, 
                username, 
                password, 
                age as u8, 
                display_name, 
                nationality, 
                pro_player_elo,
                win_rate,
                no_of_pro_games_played,
                follower_count
            );

            return Ok(Some(return_user));
        } else if is_spec == Some(1) {
            let user_row: Option<(
                u32,
                String,
                String,
                u32,
                String,
                String,
                u32,
                bool,
                bool,
                u32,
                bool,
            )> = conn.exec_first(
                r#"
                SELECT
                    u.id,
                    u.username,
                    u.password,
                    u.age,
                    u.display_name,
                    u.nationality,
                    s.player_elo,
                    s.is_chat_banned,
                    s.is_game_banned,
                    s.no_of_games_played
                FROM users u
                LEFT JOIN spectator_player_entity s
                    ON u.id = s.id
                WHERE u.id = :id
                LIMIT 1
                "#,
                params! {
                    "id" => user_id,
                }
            )?;

            let Some((id, username, password, age, display_name, nationality, spec_player_elo, is_chat_banned, is_game_banned, no_of_spec_games_played, has_pass)) = user_row else {
                return Ok(None);
            };

            let return_user: User = User::new_spec(
                id,
                username,
                password,
                age as u8,
                display_name,
                nationality,
                spec_player_elo,
                is_chat_banned,
                is_game_banned,
                no_of_spec_games_played,
                has_pass
            );

            return Ok(Some(return_user));
        } else {
            Ok(None)
        }
    }

    fn get_average_comments_by_user_id(&self, user_id: u32) -> Result<Option<f32>> {
        let mut conn = self.get_conn()?;

        let result: Option<f32> = conn.exec_first(
            r#"
            SELECT COALESCE(AVG(sub.comment_count), 0) AS avg_comments
            FROM (
                SELECT COUNT(c.id) AS comment_count
                FROM articles a
                LEFT JOIN comments c ON a.id = c.article_id
                WHERE a.author_id = :id
                GROUP BY a.id
            ) sub
            "#,
            params! {
                "id" => user_id,
            }
        )?;

        return Ok(result);
    }
}

