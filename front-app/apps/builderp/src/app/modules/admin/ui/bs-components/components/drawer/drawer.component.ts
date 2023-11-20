import { Component } from '@angular/core';
import { BsDrawerMode } from '@bs/components/drawer';
import { BsComponentsComponent } from 'app/modules/admin/ui/bs-components/bs-components.component';

@Component({
    selector   : 'ng-mf-bs-drawer',
    templateUrl: './drawer.component.html'
})
export class DrawerComponent
{
    drawerMode: BsDrawerMode;
    drawerOpened: boolean;

    /**
     * Constructor
     */
    constructor(
        private _bsComponentsComponent: BsComponentsComponent
    )
    {
        // Set the defaults
        this.drawerMode = 'side';
        this.drawerOpened = true;
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Toggle the drawer mode
     */
    toggleDrawerMode(): void
    {
        this.drawerMode = this.drawerMode === 'side' ? 'over' : 'side';
    }

    /**
     * Toggle the drawer open
     */
    toggleDrawerOpen(): void
    {
        this.drawerOpened = !this.drawerOpened;
    }

    /**
     * Drawer opened changed
     *
     * @param opened
     */
    drawerOpenedChanged(opened: boolean): void
    {
        this.drawerOpened = opened;
    }

    /**
     * Toggle the drawer
     */
    toggleDrawer(): void
    {
        // Toggle the drawer
        this._bsComponentsComponent.matDrawer.toggle();
    }
}
