package android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class FragmentCopy {
    public static void main(String[] args) {

    }

    static class MyFragmentActivity extends FragmentActivity {
        @Override
        protected void onCreate() {
            super.onCreate();
            addFragment();
        }

        private void addFragment() {
            getSupportFragmentManager().beginTransaction()
                    .add(new MyFragment())
                    .commit();
        }
    }

    static class MyFragment extends Fragment {
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            System.out.println(getContext());
        }
    }
}
