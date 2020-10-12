package com.tmjee.myathena.ui.statements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.tmjee.myathena.databinding.FragmentStatementBottomNavigationAction1Binding
import com.tmjee.myathena.R;
import com.tmjee.myathena.ui.sendBroadcast
import com.tmjee.myathena.ui.sendNotification
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import com.tmjee.myathena.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_home_drawer_statement.view.*

class StatementBottomNavigationAction1Fragment: Fragment() {

    lateinit var bindings: FragmentStatementBottomNavigationAction1Binding


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        bindings = FragmentStatementBottomNavigationAction1Binding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this

        val homeDrawerStatementFragment = parentFragment?.parentFragment as HomeDrawerStatementFragment

        val navController = findNavController()

        setupWithNavController(homeDrawerStatementFragment.bindings.bottomNavigationView, navController)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings.buttonFireNotification.setOnClickListener {
            sendNotification(requireContext())
        }

        bindings.buttonBroadcast.setOnClickListener {
            sendBroadcast(requireContext())
        }
    }




}