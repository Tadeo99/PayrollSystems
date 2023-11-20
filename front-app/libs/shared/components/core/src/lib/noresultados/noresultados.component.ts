import {
  Component, Input, ViewEncapsulation
} from '@angular/core';
import { bsAnimations } from '@ng-mf/shared/bs';

@Component({
  selector: 'ng-mf-bs-noresultados',
  templateUrl: './noresultados.component.html',
  styleUrls: ['./noresultados.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: bsAnimations
})
export class NoResultadosComponent {

  @Input()
  public length  = 0;

  constructor() {
    //
  }

}