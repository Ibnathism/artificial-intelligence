import java.util.ArrayList;
import java.util.List;

public class Player {
    private String type;
    private List<Block> blockList;

    public Player(String type) {
        this.type = type;
        blockList = new ArrayList<>();
    }


    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }
}
