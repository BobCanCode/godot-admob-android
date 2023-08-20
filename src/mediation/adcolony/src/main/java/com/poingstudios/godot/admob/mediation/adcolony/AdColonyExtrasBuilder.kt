// MIT License
//
// Copyright (c) 2023-present Poing Studios
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.poingstudios.godot.admob.mediation.adcolony

import android.os.Bundle
import com.google.android.gms.ads.mediation.MediationExtrasReceiver
import com.jirbo.adcolony.AdColonyAdapter
import com.jirbo.adcolony.AdColonyBundleBuilder
import com.poingstudios.godot.admob.core.AdNetworkExtras
import com.poingstudios.godot.admob.core.utils.LogUtils
import kotlin.String


class AdColonyExtrasBuilder : AdNetworkExtras {
    private val SHOW_PRE_POPUP_KEY = "SHOW_PRE_POPUP_KEY"
    private val SHOW_POST_POPUP_KEY = "SHOW_POST_POPUP_KEY"

    override fun buildExtras(extras: Map<String, Any>?): Bundle? {
        val showPrePopup = extras?.get(SHOW_PRE_POPUP_KEY)

        if (showPrePopup != null) {
            AdColonyBundleBuilder.setShowPrePopup(showPrePopup as Boolean)
        }
        val showPostPopup = extras?.get(SHOW_POST_POPUP_KEY)

        if (showPostPopup != null) {
            AdColonyBundleBuilder.setShowPostPopup(showPostPopup as Boolean)
        }
        LogUtils.debug("buildExtras of class : ${getAdapterClass()}")
        return AdColonyBundleBuilder.build()
    }

    override fun getAdapterClass(): Class<out MediationExtrasReceiver> {
        return AdColonyAdapter::class.java
    }
}