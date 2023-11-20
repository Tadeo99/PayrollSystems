import { Component } from '@angular/core';
import { BsAlertService } from '@bs/components/alert';
import { BsComponentsComponent } from 'app/modules/admin/ui/bs-components/bs-components.component';

@Component({
    selector   : 'ng-mf-bs-alert',
    templateUrl: './alert.component.html',
    styles     : [
        `
            bs-alert {
                margin: 16px 0;
            }
        `
    ]
})
export class AlertComponent
{
    /**
     * Constructor
     */
    constructor(
        private _bsAlertService: BsAlertService,
        private _bsComponentsComponent: BsComponentsComponent
    )
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Dismiss the alert via the service
     *
     * @param name
     */
    dismiss(name: string): void
    {
        // Dismiss
        this._bsAlertService.dismiss(name);
    }

    /**
     * Show the alert via the service
     *
     * @param name
     */
    show(name: string): void
    {
        // Show
        this._bsAlertService.show(name);
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
