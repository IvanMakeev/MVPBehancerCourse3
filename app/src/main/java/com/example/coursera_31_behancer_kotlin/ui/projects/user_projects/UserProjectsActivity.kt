package com.example.coursera_31_behancer_kotlin.ui.projects.user_projects

import android.support.v4.app.Fragment
import com.example.coursera_31_behancer_kotlin.common.SingleFragmentActivity
import com.example.coursera_31_behancer_kotlin.ui.profile.ProfileActivity

class UserProjectsActivity : SingleFragmentActivity() {

    protected override fun getFragment(): Fragment {
        if (intent != null) {
            return UserProjectsFragment.newInstance(intent.getBundleExtra(ProfileActivity.USERNAME_KEY))
        }
        throw IllegalStateException("getIntent cannot be null")
    }
}