import { NgModule } from '@angular/core';
import { BsMediaWatcherService } from './media-watcher.service';

@NgModule({
    providers: [
        BsMediaWatcherService
    ]
})
export class BsMediaWatcherModule {
    /**
     * Constructor
     */
    constructor(private _bsMediaWatcherService: BsMediaWatcherService) {
        //
    }
}
