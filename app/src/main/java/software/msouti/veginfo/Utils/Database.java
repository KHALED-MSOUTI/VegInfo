package software.msouti.veginfo.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    public static DatabaseReference ref;

    public static void setInfo(){
        ref= FirebaseDatabase.getInstance().getReference();
        ArrayList<VegListType> list= new ArrayList<>();
    /*
        list.add(new VegListType("vegetables",
                "Whattolook for",
                "Availability",
                "store",
                "howto",
                "way",
                "cookingmethods",
                "retailing",
                "advice",
                "image" ));
*/

        for (int i=0;i<=list.size()-1;i++) {
            DatabaseReference databaseReference=ref.child(list.get(i).getTitle());
            Map<String,Object> taskMap = new HashMap<>();
            taskMap.put("Title", list.get(i).getTitle());
            taskMap.put("What to look for", list.get(i).getWhatToLookFor());
            taskMap.put("Availability",list.get(i).getAvailability());
            taskMap.put("Store",list.get(i).getStore());
            taskMap.put("How To prepare",list.get(i).getHowToPrepare());
            taskMap.put("Ways To Eat",list.get(i).getWaysToEat());
            taskMap.put("cooking method",list.get(i).getCookingMethods());
            taskMap.put("Retailing",list.get(i).getRetailing());
            taskMap.put("imagePath",list.get(i).getImagePath());
            taskMap.put("advice",list.get(i).getAdvice());
            databaseReference.updateChildren(taskMap);
        }

    }


}
