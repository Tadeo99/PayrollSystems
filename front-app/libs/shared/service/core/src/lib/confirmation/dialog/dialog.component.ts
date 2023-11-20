import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BsConfirmationConfig } from '../confirmation.types';

@Component({
    selector: 'ng-mf-bs-confirmation-dialog',
    templateUrl: './dialog.component.html',
    styles: [
        `
            .bs-confirmation-dialog-panel {

                @screen md {
                    @apply w-128;
                }

                .mat-mdc-dialog-container {

                    .mat-mdc-dialog-surface {
                        padding: 0 !important;
                    }
                }
            }
        `
    ],
    encapsulation: ViewEncapsulation.None
})
export class BsConfirmationDialogComponent {
    /**
     * Constructor
     */
    constructor(@Inject(MAT_DIALOG_DATA) public data: BsConfirmationConfig) {
    }

}
