import { Component } from '@angular/core';
import { BsComponentsComponent } from 'app/modules/admin/ui/bs-components/bs-components.component';

@Component({
    selector   : 'ng-mf-bs-card',
    templateUrl: './card.component.html'
})
export class CardComponent
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
