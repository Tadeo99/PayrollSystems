import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BsCardModule, BsAlertModule, SharedModule } from '@ng-mf/shared/components/core';
import { AuthUnlockSessionComponent } from './unlock-session.component';
import { authUnlockSessionRoutes } from './unlock-session.routing';

@NgModule({
    declarations: [
        AuthUnlockSessionComponent
    ],
    imports: [
        RouterModule.forChild(authUnlockSessionRoutes),
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatProgressSpinnerModule,
        BsCardModule,
        BsAlertModule,
        SharedModule
    ]
})
export class AuthUnlockSessionModule {
}
