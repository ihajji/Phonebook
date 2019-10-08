import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'limitStrLength'
})
export class LimitStrLengthPipe implements PipeTransform {

  transform(str: string, maxLength: number): string {
    if (str.length > maxLength) {
      str = str.substr(0, maxLength - 3);
      str = str.concat('...');
    }
    return str;
  }
}
