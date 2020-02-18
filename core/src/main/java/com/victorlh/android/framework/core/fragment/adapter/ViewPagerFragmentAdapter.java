package com.victorlh.android.framework.core.fragment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.victorlh.android.framework.core.fragment.AbstractFragment;

import java.util.ArrayList;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

	private final AbstractFragment[] fragments;

	private ViewPagerFragmentAdapter(@NonNull FragmentManager fm, AbstractFragment[] fragments) {
		super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		this.fragments = fragments;
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return "asd";
	}

	public static final class Builder {
		private final ArrayList<AbstractFragment> fragments;

		public Builder() {
			this.fragments = new ArrayList<>();
		}

		public Builder addFragment(AbstractFragment fragment) {
			fragments.add(fragment);
			return this;
		}

		public ViewPagerFragmentAdapter build(FragmentManager fm) {
			AbstractFragment[] array = fragments.toArray(new AbstractFragment[0]);
			return new ViewPagerFragmentAdapter(fm, array);
		}
	}

	public static void asociar(ViewPager viewPager, TabLayout tabLayout) {
		tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
	}
}
