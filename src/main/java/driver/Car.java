package driver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Car {
    Integer m_nbOfStreets;
    List<String> m_streetList;
    Car(String data)
    {
        List<String> input= Arrays.asList(data.split(" ")).stream().collect(Collectors.toList());
        m_nbOfStreets= Integer.valueOf(input.get(0));
        m_streetList= input.subList(1,input.size());
    }

    @Override
    public String toString() {
        return m_nbOfStreets+" "+ m_streetList.toString()+"\n";
    }
}
