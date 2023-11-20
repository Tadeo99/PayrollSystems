import { NgModule } from '@angular/core';
import { DemoPlaceholderComponent } from './demo-placeholder.component';

@NgModule({
    declarations: [
        DemoPlaceholderComponent
    ],
    exports     : [
        DemoPlaceholderComponent
    ]
})
export class DemoPlaceholderModule
{
}
