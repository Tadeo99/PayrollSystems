import { NgModule } from '@angular/core';
import { BsPlatformService } from './platform.service';

@NgModule({
    providers: [
        BsPlatformService
    ]
})
export class BsPlatformModule {
    /**
     * Constructor
     */
    constructor(private _bsPlatformService: BsPlatformService) {
        //
    }
}
