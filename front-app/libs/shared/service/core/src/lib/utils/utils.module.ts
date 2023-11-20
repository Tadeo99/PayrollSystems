import { NgModule } from '@angular/core';
import { BsUtilsService } from './utils.service';

@NgModule({
    providers: [
        BsUtilsService
    ]
})
export class BsUtilsModule {
    /**
     * Constructor
     */
    constructor(private _bsUtilsService: BsUtilsService) {
    }
}
