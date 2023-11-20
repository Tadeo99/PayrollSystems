import { Directive, ElementRef } from '@angular/core';

//https://french-tech-lead.medium.com/how-to-disable-the-annoying-google-chrome-form-autocomplete-with-angular-bdcf213008b3
@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[autocompleteOff]'
})
export class AutocompleteOffDirective {
  constructor(private _el: ElementRef) {
    const w: any = window;
    const isChrome = w.chrome;
    if (isChrome) {
      this._el.nativeElement.setAttribute('autocomplete', 'off');
      this._el.nativeElement.setAttribute('autocorrect', 'off');
      this._el.nativeElement.setAttribute('autocapitalize', 'none');
      this._el.nativeElement.setAttribute('spellcheck', 'false');
    }
  }
}