use crate::domain::{
    admin::Admin, professional_player::ProfessionalPlayer, spectator_player::SpectatorPlayer,
};

pub trait UserService {
    fn get_user_by_id_adm(&self, id: u32) -> Admin;
    fn get_user_by_id_pro(&self, id: u32) -> ProfessionalPlayer;
    fn get_user_by_id_spe(&self, id: u32) -> SpectatorPlayer;

    fn create_adm(&mut self, admin: Admin) -> Admin;
    fn create_pro(&mut self, professional_player: ProfessionalPlayer) -> ProfessionalPlayer;
    fn create_spe(&mut self, spectator_player: SpectatorPlayer) -> SpectatorPlayer;

    fn update_adm(&mut self, update_id: u32, admin: Admin) -> Admin;
    fn update_pro(
        &mut self,
        update_id: u32,
        professional_player: ProfessionalPlayer,
    ) -> ProfessionalPlayer;
    fn update_spe(&mut self, update_id: u32, spectator_player: SpectatorPlayer) -> SpectatorPlayer;

    fn delete_adm(&mut self, id: u32);
    fn delete_pro(&mut self, id: u32);
    fn delete_spe(&mut self, id: u32);

    fn get_adm_by_username(&self, username: &str) -> Admin;
    fn get_pro_by_username(&self, username: &str) -> ProfessionalPlayer;
    fn get_spe_by_username(&self, username: &str) -> SpectatorPlayer;

    fn get_average_comment_per_article_adm(&self, id: u32) -> f32;
    fn get_average_comment_per_article_pro(&self, id: u32) -> f32;
    fn get_average_comment_per_article_spe(&self, id: u32) -> f32;
}
