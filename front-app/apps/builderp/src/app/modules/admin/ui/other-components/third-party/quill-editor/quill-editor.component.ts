import { Component } from '@angular/core';
import { OtherComponentsComponent } from '../../../other-components/other-components.component';

@Component({
    selector   : 'ng-mf-bs-quill-editor',
    templateUrl: './quill-editor.component.html'
})
export class QuillEditorComponent
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
