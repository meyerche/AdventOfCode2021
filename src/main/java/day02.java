import shared.loadInputFile;

import java.util.List;

public class day02 {
    public static void main(String[] args) {
        List<String> data = loadInputFile.ReadFileString("day02_input.txt");
        //data.stream().forEach(System.out::println);

        subPosition finalPos;
        finalPos = data.stream().map(subPosition::new).reduce(new subPosition(0,0), day02::sum);

        System.out.println(finalPos.getDepth() * finalPos.getHorizontalPos() );
    }

    public static subPosition sum(subPosition pos1, subPosition pos2) {
        pos1.addPos(pos2);
        return pos1;
    }

}

class subPosition {
    private Integer depth;
    private Integer horizontalPos;

    public subPosition(String pos) {
        this.depth = 0;
        this.horizontalPos = 0;

        int dist = Integer.parseInt(String.valueOf(pos.charAt(pos.length() - 1)));

        if (pos.charAt(0) == 'u') {
            this.depth -= dist;
        } else if (pos.charAt(0) == 'd') {
            this.depth += dist;
        } else if (pos.charAt(0) == 'f') {
            this.horizontalPos += dist;
        }
    }
    public subPosition(Integer d, Integer h) {
        depth = d;
        horizontalPos = h;
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
}