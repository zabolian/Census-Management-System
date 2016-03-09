import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by qizilbash on 3/1/16.
 */
public class Lister {

    private int year;
    private int countary = 2;

    private Double[] arr;
    private ArrayList<String> list = new ArrayList<String>();
    private Sheet sheet;

    private ImportData importData = new ImportData();

    private String str1 ="Data/WPP2015_POP_F01_2_TOTAL_POPULATION_MALE.XLS";
    private String str2 ="Data/WPP2015_POP_F01_3_TOTAL_POPULATION_FEMALE.XLS";
    private String str3 ="Data/WPP2015_POP_F02_POPULATION_GROWTH_RATE.XLS";
    private String str4 ="Data/WPP2015_POP_F03_RATE_OF_NATURAL_INCREASE.XLS";

    public  ArrayList<String> getList(int year, int opt) throws IOException {
        this.year = year;
        this.year -=1950;
        this.year +=5;
        switch (opt){
            case 1 :
                sheet = importData.Import(str1,0);
                break;
            case 2 :
                sheet = importData.Import(str2,0);
                break;
            case 3 :
                sheet = importData.Import(str3,0);
                break;
            case 4 :
                sheet = importData.Import(str4,0);
                break;

        }
        arr = new Double[sheet.getRows()-19];
        System.out.println(sheet.getRows());
        for (int i=0;i<sheet.getRows()-19;i++){
            arr[i]= Double.valueOf(sheet.getValues()[i+19][this.year]);
        }

        Arrays.sort(arr);
        System.out.println(arr.length);
        for (double item : arr){
            for (int i =0 ; i< sheet.getRows()-19;i++){
                if (Double.valueOf(sheet.getValues()[i+19][this.year]) == item){
                    list.add(sheet.getValues()[i][countary]);
                    break;
                }
            }
        }

        for (String item : list)
            System.out.println(item);

        return list;
    }


    public static void main(String[] args) throws IOException {
        new Lister().getList(1950,1);
    }


}
