package school.videopirateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//    public static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    public static DatabaseReference databaseReference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initializeDatabase();

        // IDEAS
        // point system
        // watch history
        // add activity transition animaition

        // TODO, learn about fragments, they will be useful


//        User myUser=new User("fukuya");
//        Video myVideo=new Video("my video",myUser.getName());
//        Comment myComment=new Comment("fukuya","test");
//        Database.addUser(myUser);
//        Database.addVideo(myVideo);
//        Database.addComment(myComment,myVideo);

        testDatabase();

//        ArrayList<Test>arr=new ArrayList<Test>();
//        arr.add(new Test(1,"a",'c'));
//        arr.add(new Test(2,"b",'d'));
//        arr.add(new Test(3,"c",'e'));
//        Database.getDatabase().getReference("test").setValue(arr);

//        needs work on this
//        Database.Add(Playlist.Default());
//        Database.Add(Video.Default());
//        Video.Default().addComment(new Comment());
//        Database.Add(Playlist.Default());

        Intent intent=new Intent(this, MainMenuActivity.class);
        startActivity(intent);

//        Database.Users()
//        CollectionReference Users=Database.GetDatabase().;
//        FirebaseFirestore db=FirebaseFirestore.getInstance().collection()
//
        // Every class for use in the database will have its own Add, Remove, and Get functions to run directly staticly from the class
        // As like in, User.Add(User), Comment.Add(Comment), Video.Add(Video)
        // CHANGE OF MIND ON THE BELOW, why not just make so to have Database.Add(thing)
        // Alright, Every class that needs to be in database gets default fns, GetTree for its reference tree,initialize somethind idk and else


        // warning to me: adding an empty tree key is BAD, it destroys the database
        // figured: it accesses the "main folder" of the database, which is the database itself

        // NOTE FOR USER PICTURES, RECOMMEND SOME WHEN IN THE PROCESS THAT PFPs SHOULD BE 512x512, DO THIS SCALING ALSO DURING RUNTIME WHEN MAKING AND LOADING PICTURES

        // TO ADD OBJECTS INTO THE DATABASE, THE STRUCTURE IS TREE/2TREE/3TREE, IN WHICH, FOR EACH TREE YOU CAN STORE DATA SEPARATELY AND ACT ON IT AS A CLASS HERE

//        Intent intent=new Intent(this, MainMenuActivity.class);
//        startActivity(intent);
    }
    public void testDatabase() {
        Database.addUser(User.Default());
        Database.addPlaylist(Playlist.Default());
        Database.addVideo(Video.Default());
        Database.addComment(Comment.Default(),Video.Default());
    }
    public void initializeDatabase() {
        DatabaseReference videosRef=Database.getRef("videos");
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    videosRef.setValue("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference playlistsRef=Database.getRef("playlists");
        playlistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    playlistsRef.setValue("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference usersRef=Database.getRef("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    usersRef.setValue("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public class Test {
        public Integer getItest() {
            return itest;
        }

        public void setItest(Integer itest) {
            this.itest = itest;
        }

        public String getStest() {
            return stest;
        }

        public void setStest(String stest) {
            this.stest = stest;
        }

        private Integer itest;
        private String stest;
//        public Character ctest;

        public Test() {
            itest=0;
            stest="0";
//            ctest='0';
        }

        public Test(Integer i,String s, Character c) {
            itest=i;
            stest=s;
//            ctest=c;
        }
    }
}