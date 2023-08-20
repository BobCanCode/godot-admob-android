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

package com.poingstudios.godot.admob.ads

import android.util.ArraySet
import com.google.android.ump.UserMessagingPlatform
import com.poingstudios.godot.admob.ads.converters.convertToGodotDictionary
import com.poingstudios.godot.admob.ads.ump.PoingGodotAdMobConsentForm
import com.poingstudios.godot.admob.core.utils.LogUtils
import org.godotengine.godot.Dictionary
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot


class PoingGodotAdMobUserMessagingPlatform(godot: Godot?) : org.godotengine.godot.plugin.GodotPlugin(godot){
    private val consentForms = mutableListOf<PoingGodotAdMobConsentForm?>()

    override fun getPluginName(): String {
        return this::class.simpleName.toString()
    }

    override fun getPluginSignals(): MutableSet<SignalInfo> {
        val signals: MutableSet<SignalInfo> = ArraySet()
        signals.add(SignalInfo("on_consent_form_load_success_listener", Integer::class.java))
        signals.add(SignalInfo("on_consent_form_load_failure_listener", Dictionary::class.java))
        signals.add(PoingGodotAdMobConsentForm.SignalInfos.onConsentFormDismissed)
        return signals
    }

    @UsedByGodot
    fun load_consent_form(){
        activity!!.runOnUiThread {
            LogUtils.debug("load_consent_form")
            UserMessagingPlatform.loadConsentForm(
                activity!!,
                {
                    val consentForm = PoingGodotAdMobConsentForm(consentForms.size, it, activity!!, godot, pluginName).apply {
                        consentForms.add(this)
                    }
                    emitSignal("on_consent_form_load_success_listener", consentForm.UID)
                },
                {
                    emitSignal("on_consent_form_load_failure_listener", it.convertToGodotDictionary())
                }
            )
        }
    }

    @UsedByGodot
    fun show(UID : Int){
        consentForms[UID]?.show()
    }
}