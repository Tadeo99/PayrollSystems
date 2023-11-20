import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { BsCardModule } from '@bs/components/card';
import { SharedModule } from '@ng-mf/shared/components/core';
import { PricingModernComponent } from 'app/modules/admin/pages/pricing/modern/modern.component';
import { pricingModernRoutes } from 'app/modules/admin/pages/pricing/modern/modern.routing';

@NgModule({
    declarations: [
        PricingModernComponent
    ],
    imports     : [
        RouterModule.forChild(pricingModernRoutes),
        MatButtonModule,
        MatIconModule,
        BsCardModule,
        SharedModule
    ]
})
export class PricingModernModule
{
}
