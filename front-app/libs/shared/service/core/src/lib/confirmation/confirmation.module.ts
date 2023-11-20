import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { BsConfirmationService } from './confirmation.service';
import { BsConfirmationDialogComponent } from './dialog/dialog.component';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        BsConfirmationDialogComponent
    ],
    imports: [
        MatButtonModule,
        MatDialogModule,
        MatIconModule,
        CommonModule
    ],
    providers: [
        BsConfirmationService
    ]
})
export class BsConfirmationModule {
    /**
     * Constructor
     */
    constructor(private _bsConfirmationService: BsConfirmationService) {
    }
}
