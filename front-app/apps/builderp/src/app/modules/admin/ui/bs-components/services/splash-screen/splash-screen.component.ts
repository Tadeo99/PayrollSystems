import { Component } from '@angular/core';
import { BsComponentsComponent } from 'app/modules/admin/ui/bs-components/bs-components.component';

@Component({
    selector   : 'ng-mf-bs-splash-screen',
    templateUrl: './splash-screen.component.html'
})
export class SplashScreenComponent
{
    /**
     * Constructor
     */
    constructor(private _bsComponentsComponent: BsComponentsComponent)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Toggle the drawer
     */
    toggleDrawer(): void
    {
        // Toggle the drawer
        this._bsComponentsComponent.matDrawer.toggle();
    }
}
