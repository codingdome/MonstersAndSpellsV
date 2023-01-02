package if21b151.application.card.repository;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.application.user.model.User;
import if21b151.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    @Override
    public int countPackages() {
        int count = 0;

        String sql = """
                select count (*) from packages
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public void addNewPackage(CardPackage cardPackage) {

        int packageID = countPackages() + 1;
        List<String> cardIDs = new ArrayList<>();
        for (Card card : cardPackage.getPackageCards()) {
            //1 add card to cards
            insertCardInCards(card);
            //2 add ID to cardIDs Array
            cardIDs.add(card.getId());
        }
        //3 add IDs to Package Table
        addIDToPackages(packageID, cardIDs);
    }

    private void insertCardInCards(Card card) {

        String sql = """
                insert into cards (id,name,username,damage,deck, trade) values(?,?,?,?,?,?)
                """;

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, card.getId());
            statement.setString(2, card.getName());
            statement.setString(3, "0");
            statement.setDouble(4, card.getDamage());
            statement.setInt(5, 0);
            statement.setInt(6, 0);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addIDToPackages(int id, List<String> IDs) {
        String sql = """
                insert into packages (id,card_id_1,card_id_2,card_id_3,card_id_4,card_id_5) values(?,?,?,?,?,?)
                """;

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, IDs.get(0));
            statement.setString(3, IDs.get(1));
            statement.setString(4, IDs.get(2));
            statement.setString(5, IDs.get(3));
            statement.setString(6, IDs.get(4));

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
