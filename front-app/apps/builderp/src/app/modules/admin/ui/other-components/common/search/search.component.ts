import { Component } from '@angular/core';
import { OtherComponentsComponent } from '../../../other-components/other-components.component';

@Component({
    selector   : 'ng-mf-bs-search',
    templateUrl: './search.component.html'
})
export class SearchComponent
{
    /**
     * Constructor
     */
    constructor(private _otherComponentsComponent: OtherComponentsComponent)
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
        this._otherComponentsComponent.matDrawer.toggle();
    }
}
