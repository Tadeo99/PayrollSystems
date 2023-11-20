import { ModuleWithProviders, NgModule } from '@angular/core';
import { BsConfigService } from './config.service';
import { BS_APP_CONFIG } from './config.constants';

@NgModule()
export class BsConfigModule {
    /**
     * Constructor
     */
    constructor(private _bsConfigService: BsConfigService) {
        //
    }

    /**
     * forRoot method for setting user configuration
     *
     * @param config
     */
    static forRoot(config: any): ModuleWithProviders<BsConfigModule> {
        return {
            ngModule: BsConfigModule,
            providers: [
                {
                    provide: BS_APP_CONFIG,
                    useValue: config
                }
            ]
        };
    }
}
