import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { BsCardModule, SharedModule } from '@ng-mf/shared/components/core';
import { AuthConfirmationRequiredComponent } from './confirmation-required.component';
import { authConfirmationRequiredRoutes } from './confirmation-required.routing';

@NgModule({
    declarations: [
        AuthConfirmationRequiredComponent
    ],
    imports: [
        RouterModule.forChild(authConfirmationRequiredRoutes),
        MatButtonModule,
        BsCardModule,
        SharedModule
    ]
})
export class AuthConfirmationRequiredModule {
}
