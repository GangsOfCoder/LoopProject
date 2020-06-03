package com.app.base.ui.dashboard.ui.tab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.base.R
import com.app.base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tab2.*


class Tab2Fragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        textView23.setOnClickListener(this)
        textView24.setOnClickListener(this)
        textView25.setOnClickListener(this)
        textView26.setOnClickListener(this)
        textView27.setOnClickListener(this)
        textView28.setOnClickListener(this)
        textView30.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.textView23->{

            }
            R.id.textView24->{

            }
            R.id.textView25->{

            }
            R.id.textView26->{

            }
            R.id.textView27->{

            }
            R.id.textView28->{

            }
            R.id.textView29->{

            }
            R.id.textView30->{

            }
        }
    }
}
