package driver;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

public class Solver {
    Integer m_simulationTime;
    Integer m_nbIntersections;
    Integer m_nbStreets;
    Integer m_nbCars;
    Integer m_bonus;
    List<Street> m_streetsList=new ArrayList<>();
    Map<String, Integer> m_streetIndexMap=new HashMap<>();
    List<Car> m_carsList=new ArrayList<>();
    Set<Street> m_usefulStreetsList=new LinkedHashSet<>();
    Map<String, Integer> m_streetScore=new HashMap<>();
    Double m_averageTime=0.;
    Double m_averageCarOnStreet=0.;
    Map<Integer, List<Street>> m_intersectionIdStreetMap=new HashMap<>(); //Map of end of intersections and the corresponding streets
    Solver(String fileName) {
        InputStream inputStream = Driver.class.getClassLoader().getResourceAsStream(fileName+".txt");
        Scanner scanner=new Scanner(inputStream);
        List<String> input= Arrays.asList(scanner.nextLine().split(" ")).stream().collect(Collectors.toList());
        m_simulationTime=Integer.valueOf(input.get(0));
        m_nbIntersections=Integer.valueOf(input.get(1));
        m_nbStreets=Integer.valueOf(input.get(2));
        m_nbCars=Integer.valueOf(input.get(3));
        m_bonus=Integer.valueOf(input.get(4));
        for(Integer i=0; i<m_nbStreets; ++i)
        {
            Street s=new Street(scanner.nextLine());
            m_streetsList.add(s);
            m_streetIndexMap.put(s.m_streetName,i);
            m_averageTime+=s.m_duration;
        }
        m_averageTime/=m_nbStreets;
        for(Integer i=0; i<m_nbCars; ++i)
        {
            Car c=new Car(scanner.nextLine());
            m_carsList.add(c);
            for(String streetName:c.m_streetList)
            {
                m_usefulStreetsList.add(m_streetsList.get(m_streetIndexMap.get(streetName)));
                m_streetScore.putIfAbsent(streetName,0);
                m_streetScore.put(streetName, m_streetScore.get(streetName)+1);
            }
        }
        m_averageCarOnStreet= m_streetScore.values().stream().mapToInt(Integer::valueOf).average().orElse(0.);
        scanner.close();
    }

    void buildIntersectionIdStreetMap(Boolean keepUsedStreets)
    {
        for(Street s: keepUsedStreets? m_usefulStreetsList: m_streetsList)
        {
            m_intersectionIdStreetMap.computeIfAbsent(s.m_endIntersection,x->new ArrayList<>());
            m_intersectionIdStreetMap.get(s.m_endIntersection).add(s);
        }
    }

//    LinkedHashMap<String, Integer> sortedStreetScore()
//    {
//        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
//        m_streetScore.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue())
//                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
//        return sortedMap;
//    }

    String intersectionIdStreetGroupSolver(Integer time, Boolean keepUsedStreets, Boolean useStreetScore)
    {
        buildIntersectionIdStreetMap(keepUsedStreets);
        String result=m_intersectionIdStreetMap.size()+"\n";
        for(Map.Entry<Integer, List<Street>> keyValue:m_intersectionIdStreetMap.entrySet())
        {
            result+=keyValue.getKey()+"\n";
            List<Street> streetsList=keyValue.getValue();
            result+=streetsList.size()+"\n";
            for(Street s: streetsList)
            {
//                Integer weightedTime= (int) Math.ceil(m_streetScore.get(s.m_streetName)/m_averageTime);
                Integer weightedTime= (int) Math.ceil(m_streetScore.get(s.m_streetName)/m_averageCarOnStreet);
                if(weightedTime==0) weightedTime=1;
                result+=s.m_streetName+" "+ (useStreetScore? weightedTime: time)+"\n";
            }
        }
        return result;
    }


    @Override
    public String toString() {
        return m_simulationTime+" "+ m_nbIntersections+" "+ m_nbStreets+" "+ m_nbCars+" "+ m_bonus+"\n"+ m_streetsList.toString()+m_carsList.toString();
    }
}
