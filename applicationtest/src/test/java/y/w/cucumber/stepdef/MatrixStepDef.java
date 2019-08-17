package y.w.cucumber.stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class MatrixStepDef {
    private List<List<String>> board;

    @Given("a board like this")
    public void a_board_like_this(DataTable dataTable) {
        this.board = new ArrayList<>();
        for (List<String> r : dataTable.asLists())
            this.board.add(new ArrayList<>(r));
    }

    @When("player x plays in row {int}, column {int}")
    public void player_x_plays_in_row_column(Integer row, Integer col) {
        // Write code here that turns the phrase above into concrete actions
        board.get(row).set(col, "x");
    }

    @Then("the board should looke like this:")
    public void the_board_should_looke_like_this(DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.

        dataTable.diff(DataTable.create(board));
    }

}
