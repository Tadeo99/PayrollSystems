import { NgModule } from '@angular/core';
import { BsSplashScreenService } from './splash-screen.service';

@NgModule({
    providers: [
        BsSplashScreenService
    ]
})
export class BsSplashScreenModule {
    /**
     * Constructor
     */
    constructor(private _bsSplashScreenService: BsSplashScreenService) {
    }
}
