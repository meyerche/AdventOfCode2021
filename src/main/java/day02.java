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

class subPosition {
    private Integer depth;
    private Integer horizontalPos;
    private Integer aim;

    public subPosition(String pos) {
        this.depth = 0;
        this.horizontalPos = 0;
        this.aim = 0;

        int dist = Integer.parseInt(String.valueOf(pos.charAt(pos.length() - 1)));

        if (pos.charAt(0) == 'u') {
            //this.depth -= dist;
            this.aim -= dist;
        } else if (pos.charAt(0) == 'd') {
            //this.depth += dist;
            this.aim += dist;
        } else if (pos.charAt(0) == 'f') {
            this.horizontalPos += dist;
        }
    }

    public subPosition(Integer d, Integer h, Integer a) {
        this.depth = d;
        this.horizontalPos = h;
        this.aim = a;
    }

    public subPosition() {
        this.depth = 0;
        this.horizontalPos = 0;
        this.aim = 0;

    }

    public Integer getDepth() {
        return this.depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getHorizontalPos() {
        return this.horizontalPos;
    }

    public void setHorizontalPos(Integer horizontalPos) {
        this.horizontalPos = horizontalPos;
    }

    public void addPos(subPosition pos) {
        this.depth += pos.depth;
        this.horizontalPos += pos.horizontalPos;
    }

    public void movePos(subPosition pos) {
        if (pos.horizontalPos != 0){
            this.depth += (this.aim * pos.horizontalPos);
            this.horizontalPos += pos.horizontalPos;
        }
        this.aim += pos.aim;
    }
}