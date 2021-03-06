/**
 * Copyright (c) 2012 Todoroo Inc
 *
 * See the file "LICENSE" for the full license governing this code.
 */
package com.todoroo.astrid.service;

import android.content.Intent;
import android.net.Uri;

import com.timsu.astrid.R;

public abstract class MarketStrategy {

    /**
     * @param packageName
     * @return an intent to launch market with this package
     */
    abstract public Intent generateMarketLink(String packageName);

    /**
     * @return if this market has power pack
     */
    public boolean includesPowerPack() {
        return true;
    }

    /**
     * @return if this market has locale plugin
     */
    public boolean includesLocalePlugin() {
        return true;
    }

    /**
     * @return true if addons should be shown
     */
    public boolean showAddonMenu() {
        return true;
    }

    public int[] excludedSettings() {
        return null;
    }

    /**
     * @return true if ideas tab should be shown
     */
    public boolean allowIdeasTab() {
        return true;
    }

    public static class AndroidMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pname:" + //$NON-NLS-1$
                            packageName));
        }

    }

    public static class WebMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://weloveastrid.com/store")); //$NON-NLS-1$
        }

    }

    public static class AmazonMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + //$NON-NLS-1$
                            packageName));
        }

        @Override
        public boolean includesLocalePlugin() {
            return false;
        }

        @Override
        public boolean showAddonMenu() {
            return !isKindleFire();
        }

        /**
         * @return true if the device is a kindle fire and needs special treatment
         */
        public static boolean isKindleFire() {
            return android.os.Build.MANUFACTURER.equals("Amazon") && //$NON-NLS-1$
                android.os.Build.MODEL.contains("Kindle"); //$NON-NLS-1$
        }

        @Override
        public int[] excludedSettings() {
            return new int[] {
                R.string.p_theme_widget,
                R.string.p_voicePrefSection,
                R.string.p_end_at_deadline,
                R.string.p_swipe_lists_performance_key,
                R.string.p_field_missed_calls
            };
        }

    }

    public static class NookMarketStrategy extends MarketStrategy {

        @Override
        public Intent generateMarketLink(String packageName) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pname:" + //$NON-NLS-1$
                            packageName));
        }

        @Override
        public boolean includesLocalePlugin() {
            return false;
        }

        @Override
        public boolean showAddonMenu() {
            return false;
        }

        @Override
        public boolean allowIdeasTab() {
            return false;
        }

        @Override
        public int[] excludedSettings() {
            return new int[] {
                R.string.p_theme_widget,
                R.string.p_voicePrefSection,
                R.string.p_end_at_deadline,
                R.string.p_swipe_lists_performance_key,
                R.string.p_field_missed_calls
            };
        }

    }

}
