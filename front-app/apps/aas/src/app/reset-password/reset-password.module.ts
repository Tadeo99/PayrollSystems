import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BsCardModule, BsAlertModule, SharedModule } from '@ng-mf/shared/components/core';
import { AuthResetPasswordComponent } from './reset-password.component';
import { authResetPasswordRoutes } from './reset-password.routing';

@NgModule({
    declarations: [
        AuthResetPasswordComponent
    ],
    imports: [
        RouterModule.forChild(authResetPasswordRoutes),
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
export class AuthResetPasswordModule {
}
