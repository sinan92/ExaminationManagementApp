import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ingeleverd'
})
export class IngeleverdPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value !== null ? 'fa-check' : 'fa-times';
  }

}
