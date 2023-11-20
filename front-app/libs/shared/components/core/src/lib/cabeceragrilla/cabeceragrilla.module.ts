import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CabeceraGrillaComponent } from './cabeceragrilla.component';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { TranslocoModule } from '@ngneat/transloco';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({   
    declarations: [
        CabeceraGrillaComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatIconModule,
        MatInputModule,
        MatButtonModule,
        MatFormFieldModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        TranslocoModule
    ],
    exports: [
        CabeceraGrillaComponent
    ]
})
export class BsCabeceraGrillaModule {
}
