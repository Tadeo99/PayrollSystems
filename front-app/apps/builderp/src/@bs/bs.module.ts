import { NgModule, Optional, SkipSelf } from '@angular/core';
import { MATERIAL_SANITY_CHECKS } from '@angular/material/core';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import {
    BsConfirmationModule, BsLoadingModule,
    BsMediaWatcherModule, BsSplashScreenModule,
    BsUtilsModule, BsPlatformModule
} from '@ng-mf/shared/service/core';

@NgModule({
    imports: [
        BsConfirmationModule,
        BsLoadingModule,
        BsMediaWatcherModule,
        BsPlatformModule,
        BsSplashScreenModule,
        BsUtilsModule
    ],
    providers: [
        {
            // Disable 'theme' sanity check
            provide: MATERIAL_SANITY_CHECKS,
            useValue: {
                doctype: true,
                theme: false,
                version: true
            }
        },
        {
            // Use the 'fill' appearance on Angular Material form fields by default
            provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
            useValue: {
                appearance: 'fill'
            }
        }
    ]
})
export class BsModule {
    /**
     * Constructor
     */
    constructor(@Optional() @SkipSelf() parentModule?: BsModule) {
        if (parentModule) {
            throw new Error('BsModule has already been loaded. Import this module in the AppModule only!');
        }
    }
}
