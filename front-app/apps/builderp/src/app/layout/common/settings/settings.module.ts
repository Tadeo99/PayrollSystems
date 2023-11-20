import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BsDrawerModule } from '@ng-mf/shared/components/core';
import { SettingsComponent } from './settings.component';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
    declarations: [
        SettingsComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        MatIconModule,
        MatTooltipModule,
        BsDrawerModule,
        MatButtonModule
    ],
    exports: [
        SettingsComponent
    ]
})
export class SettingsModule {
}
