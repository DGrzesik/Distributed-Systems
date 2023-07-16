package resorts;


import java.util.ArrayList;
import java.util.List;

public enum Resort {
    PULAWY("Pulawy"),
    WANKOWA("Wankowa"),
    ARENA("Slotwiny Arena"),
    SLOTWINY("Slotwiny"),
    TYLICZ("Tylicz"),
    JAWORZYNA("Jaworzyna Krynicka");

    private final String name;

    Resort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static List<String> getResortNames(){
        List<String> resorts = new ArrayList<>();
        for(Resort resort : values()){
            resorts.add(resort.getName().toLowerCase());
        }
        return resorts;
    }
}
