package com.sontruong.sof.ui.userlist

import android.os.Bundle
import androidx.lifecycle.Observer
import com.sontruong.sof.ui.UserListAdapter
import com.sontruong.sof.ui.base.BaseActivity
import com.sontruong.sof.utils.viewModelProvider
import com.sontruong.sof.R
import com.sontruong.sof.data.NetworkState
import com.sontruong.sof.ui.ListPaddingDecoration
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : BaseActivity() {

    private lateinit var  viewModel: UsersViewModel
    private lateinit var adapter: UserListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        viewModel = viewModelProvider(viewModelFactory)
        user_list.addItemDecoration(ListPaddingDecoration(this, 0, 0))
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        adapter = UserListAdapter {
            viewModel.retry()
        }
        user_list.adapter = adapter
        viewModel.users.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        viewModel.refreshState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}