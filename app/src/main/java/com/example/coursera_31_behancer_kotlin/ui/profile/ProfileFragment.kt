package com.example.coursera_31_behancer_kotlin.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.coursera_31_behancer_kotlin.R
import com.example.coursera_31_behancer_kotlin.common.PresenterFragment
import com.example.coursera_31_behancer_kotlin.common.RefreshOwner
import com.example.coursera_31_behancer_kotlin.common.Refreshable
import com.example.coursera_31_behancer_kotlin.data.Storage
import com.example.coursera_31_behancer_kotlin.data.model.user.User
import com.example.coursera_31_behancer_kotlin.ui.projects.user_projects.UserProjectsActivity
import com.example.coursera_31_behancer_kotlin.ui.projects.user_projects.UserProjectsFragment
import com.example.coursera_31_behancer_kotlin.utils.DateUtils
import com.squareup.picasso.Picasso

class ProfileFragment : PresenterFragment(), ProfileView, Refreshable {

    companion object {
        const val PROFILE_KEY = "PROFILE_KEY"

        fun newInstance(args: Bundle): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private var refreshOwner: RefreshOwner? = null
    private lateinit var errorView: View
    private lateinit var profileView: View
    private lateinit var username: String
    private var storage: Storage? = null
    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileCreatedOn: TextView
    private lateinit var profileLocation: TextView
    private lateinit var allProjectsButton: Button
    private val onProjectsClickListener = View.OnClickListener {
        val intent = Intent(activity, UserProjectsActivity::class.java)
        val args = Bundle()
        args.putString(UserProjectsFragment.USER_ID, username)
        intent.putExtra(ProfileActivity.USERNAME_KEY, args)
        startActivity(intent)
    }
    @InjectPresenter
    internal lateinit var localPresenter: ProfilePresenter



    override fun getPresenter(): ProfilePresenter {
        return localPresenter
    }

    @ProvidePresenter
    fun providePresenter() = ProfilePresenter(this, storage!!)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        storage = if (context is Storage.StorageOwner) context.obtainStorage() else null
        refreshOwner = if (context is RefreshOwner) context else null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        errorView = view.findViewById(R.id.errorView)
        profileView = view.findViewById(R.id.view_profile)
        profileImage = view.findViewById(R.id.iv_profile)
        profileName = view.findViewById(R.id.tv_display_name_details)
        profileCreatedOn = view.findViewById(R.id.tv_created_on_details)
        profileLocation = view.findViewById(R.id.tv_location_details)
        allProjectsButton = view.findViewById(R.id.btn_all_projects)
        allProjectsButton.setOnClickListener(onProjectsClickListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            username = arguments!!.getString(PROFILE_KEY)!!
        }

        if (activity != null) {
            activity!!.title = username
        }
        profileView.visibility = View.VISIBLE
        onRefreshData()
    }

    override fun onRefreshData() {
        localPresenter.getProfile(username)
    }

    override fun bind(user: User) {
        Picasso.with(context)
            .load(user.image!!.photoUrl)
            .fit()
            .into(profileImage)
        profileName.text = user.displayName
        profileCreatedOn.text = DateUtils.format(user.createdOn)
        profileLocation.text = user.location
    }

    override fun onDetach() {
        storage = null
        refreshOwner = null

        super.onDetach()
    }

    override fun showProfile() {
        errorView.visibility = View.GONE
        profileView.visibility = View.VISIBLE
    }

    override fun showRefresh() {
        refreshOwner!!.setRefreshState(true)
    }

    override fun hideRefresh() {
        refreshOwner!!.setRefreshState(false)
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        profileView.visibility = View.GONE
    }
}