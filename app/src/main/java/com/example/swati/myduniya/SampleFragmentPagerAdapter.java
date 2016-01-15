package com.example.swati.myduniya;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 10;
    private String tabTitles[] = new String[] { "Sports", "Business","Education","Technology","World","Top Stories","India","Lifestyle","Entertainment","Opinion"};

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f=null;
        switch(position){
            case 0:
                f=BreakingNewsFragment.newInstance("sports");
                break;
            case 1:
                f=BreakingNewsFragment.newInstance("business");
                break;
            case 2:
                f=BreakingNewsFragment.newInstance("education");
                break;

            case 3:
                f=BreakingNewsFragment.newInstance("tech-reviews");
                break;
            case 4:
                f=BreakingNewsFragment.newInstance("world");
                break;
            case 5:
                f=BreakingNewsFragment.newInstance("top-news");
                break;
            case 6:
                f=BreakingNewsFragment.newInstance("india");
                break;
            case 7:
                f=BreakingNewsFragment.newInstance("lifestyle");
                break;
            case 8:
                f=BreakingNewsFragment.newInstance("entertainment");
                break;
            case 9:
                f=BreakingNewsFragment.newInstance("opinion");
                break;
            case 10:
                f=BreakingNewsFragment.newInstance("business");
                break;


        }
        return f;

    }



    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
