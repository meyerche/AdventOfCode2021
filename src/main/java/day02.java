import models.subPosition;
import shared.loadInputFile;

import java.util.List;

public class day02 {
    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day02_input.txt");

        subPosition finalPos;
        finalPos = data.stream().map(subPosition::new).reduce(new subPosition(0,0,0), day02::move);

        System.out.println(finalPos.getDepth() * finalPos.getHorizontalPos() );
    }

    public static subPosition sum(subPosition pos1, subPosition pos2) {
        pos1.addPos(pos2);
        return pos1;
    }

    public static subPosition move(subPosition pos1, subPosition pos2) {
        pos1.movePos(pos2);
        return pos1;
    }
}

