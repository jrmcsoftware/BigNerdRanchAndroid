package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CrimeListFragment extends ListFragment {

    private CrimeLab mCrimeLab;
    private boolean mSubtitleVisible = false;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.crimes_title);

        mCrimeLab = CrimeLab.get();
        final CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimeLab.getCrimeList(), getActivity());
        setListAdapter(crimeAdapter);

        setRetainInstance(true);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;

        switch (item.getItemId()) {
        case R.id.menu_item_new_crime:
            Crime crime = new Crime();
            mCrimeLab.addCrime(crime);
            final Intent intent = newCrimePagerIntent(crime.getId());
            startActivityForResult(intent, 0);
            result = true;
            break;
        case R.id.menu_item_show_subtitle:
            toggleSubTitle(item);
            result = true;
            break;
        default:
            result = super.onOptionsItemSelected(item);
            break;
        }

        return result;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void toggleSubTitle(MenuItem item) {
        ActionBar actionBar = getActivity().getActionBar();
        CharSequence subTitle = actionBar.getSubtitle();

        if (subTitle == null) {
            actionBar.setSubtitle(R.string.subtitle);
            item.setTitle(R.string.hide_subtitle);
            mSubtitleVisible = false;
        } else {
            actionBar.setSubtitle(null);
            item.setTitle(R.string.show_subtitle);
            mSubtitleVisible = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(final ListView listView, final View v, final int position, final long id) {
        final Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Log.i(this.getClass().getSimpleName(), "" + position);
        final Intent intent = newCrimePagerIntent(crime.getId());
        startActivity(intent);
    }

    Intent newCrimePagerIntent(UUID uuid) {
        final Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.CRIME_KEY, uuid);

        return intent;
    }

}
