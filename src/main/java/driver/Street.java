package driver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Street {
    Integer m_startIntersection;
    Integer m_endIntersection;
    String m_streetName;
    Integer m_duration;
    Street(String data){
        List<String> input= Arrays.asList(data.split(" ")).stream().collect(Collectors.toList());
        m_startIntersection=Integer.valueOf(input.get(0));
        m_endIntersection=Integer.valueOf(input.get(1));
        m_streetName=input.get(2);
        m_duration=Integer.valueOf(input.get(3));
    }

    @Override
    public String toString() {
        return m_startIntersection+" "+ m_endIntersection+" "+ m_streetName +" "+m_duration+"\n";
    }
}
