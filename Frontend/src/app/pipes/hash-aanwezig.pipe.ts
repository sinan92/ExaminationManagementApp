import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'hashAanwezig'
})
export class HashAanwezigPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value === null ? '-' : value;
  }

}
