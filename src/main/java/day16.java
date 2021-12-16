import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class day16 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(day16.class.getClassLoader().getResourceAsStream("day16_input.txt"));
        String data = "";

        //only one line
        if (scanner.hasNext()) data = scanner.nextLine();

        ProcessCode(data.split(""));

    }

    private static void ProcessCode(String[] arr) {
        Deque<String> hexCode = new ArrayDeque<>(Arrays.asList(arr));
        Deque<String> binCode = new ArrayDeque<>(); //holds leftover binary digits

        int version;
        int versionSum = 0;
        int packetTypeID;
        int literalValue;
        String lengthTypeID;

        while (!hexCode.isEmpty()) {
            //start each sequence with the version code
            version = GetVersion(hexCode.pollFirst(), binCode);

            //next add another digit to get the packet type ID
            packetTypeID = GetPacketTypeID(hexCode.pollFirst(), binCode);

            if (packetTypeID == 4) {
                literalValue = ReadLiteralValue(hexCode, binCode);
            } else {
                if (binCode.pollFirst().equals("0")) {
                    //Process sub-packets by bit length
                    ProcessSubPacketsByBitLength(hexCode, binCode);
                } else {
                    //Process sub-packets by number of sub-packets
                };
            }
        }
    }

    private static void ProcessSubPacketsByBitLength(Deque<String> hexCode, Deque<String> binCode) {
        while (binCode.size() < 15) AddHexDigitToCode(hexCode.pollFirst(), binCode);
        int subPacketLength = Integer.parseInt(
                IntStream.range(0,15).mapToObj(d -> binCode.pollFirst()).collect(Collectors.joining("")),
                2
        );

        while (binCode.size() < subPacketLength) AddHexDigitToCode(hexCode.pollFirst(), binCode);
        while (hexCode.peekFirst().equals("0")) AddHexDigitToCode(hexCode.pollFirst(), binCode);

        //do something with sub-packet???

        binCode.clear();  //do nothing for now
    }

    private static int ReadLiteralValue(Deque<String> hexCode, Deque<String> binCode) {
        StringBuilder binLiteralValue = new StringBuilder();
        String notLastGroup;

        do {
            if (!binCode.isEmpty()) {
                notLastGroup = binCode.pollFirst();
            } else {
                AddHexDigitToCode(hexCode.poll(), binCode);
                notLastGroup = binCode.pollFirst();
            }

            if (binCode.size() < 4) AddHexDigitToCode(hexCode.pollFirst(), binCode);
            binLiteralValue.append(IntStream.range(0, 4)
                    .mapToObj(i -> binCode.removeFirst())
                    .collect(Collectors.joining("")));
        } while (notLastGroup.equals("1"));

        binCode.clear();

        return Integer.parseInt(binLiteralValue.toString());
    }

    private static void AddHexDigitToCode(String hexDigit, Deque<String> binCode) {
        Arrays.stream(Integer.toBinaryString(Integer.parseInt(hexDigit,16)).split(""))
                .forEach(d -> binCode.addLast(d));
    }

    private static int GetPacketTypeID(String hexDigit, Deque<String> binCode) {
        binCode.addLast(Integer.toBinaryString(Integer.parseInt(hexDigit)));

        //read first three digits from binary code stream to get packet type ID
        String binPacket = IntStream.rangeClosed(0,3).mapToObj(i -> binCode.removeFirst()).collect(Collectors.joining(""));

        return Integer.parseInt(binPacket,2);
    }

    private static int GetVersion(String hexDigit, Deque<String> binCode) {
        int decValue = Integer.parseInt(hexDigit,16);
        binCode.addLast(Integer.toBinaryString(decValue & 1));

        return decValue >> 1; //bit shift to the right by 1
    }
}
