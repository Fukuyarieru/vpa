package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyDatabase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    private static DatabaseReference databaseReference = database.getReference();

    public static DatabaseReference GetReference(String ref) {
        if (ref=="") {
            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
        }
        return database.getReference(ref);
    }
}
