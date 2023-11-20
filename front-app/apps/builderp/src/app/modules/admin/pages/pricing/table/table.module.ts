import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { BsCardModule } from '@bs/components/card';
import { SharedModule } from '@ng-mf/shared/components/core';
import { PricingTableComponent } from 'app/modules/admin/pages/pricing/table/table.component';
import { pricingTableRoutes } from 'app/modules/admin/pages/pricing/table/table.routing';

@NgModule({
    declarations: [
        PricingTableComponent
    ],
    imports     : [
        RouterModule.forChild(pricingTableRoutes),
        MatButtonModule,
        MatIconModule,
        BsCardModule,
        SharedModule
    ]
})
export class PricingTableModule
{
}
